// ==========================================================================
// SMART COLLEGE ANNOUNCEMENT & NOTIFICATION MANAGEMENT SYSTEM
// Modern Vanilla JS: Micro-interactions, Live Search, Theme Toggle & Modals
// ==========================================================================

document.addEventListener('DOMContentLoaded', () => {
    initTheme();
    initNotificationDropdown();
    initLiveSearch();
    initAcknowledgementModal();
    initCopyButtons();
});

// 1. Theme Management (Dark / Light Mode)
function initTheme() {
    const savedTheme = localStorage.getItem('au_theme') || 'light';
    document.documentElement.setAttribute('data-theme', savedTheme);

    const toggleBtn = document.getElementById('theme-toggle-btn');
    if (toggleBtn) {
        updateThemeToggleIcon(toggleBtn, savedTheme);
        toggleBtn.addEventListener('click', () => {
            const currentTheme = document.documentElement.getAttribute('data-theme');
            const newTheme = currentTheme === 'dark' ? 'light' : 'dark';
            document.documentElement.setAttribute('data-theme', newTheme);
            localStorage.setItem('au_theme', newTheme);
            updateThemeToggleIcon(toggleBtn, newTheme);
        });
    }
}

function updateThemeToggleIcon(btn, theme) {
    btn.innerHTML = theme === 'dark' ? '☀️' : '🌙';
    btn.setAttribute('title', `Switch to ${theme === 'dark' ? 'Light' : 'Dark'} Mode`);
}

// 2. Notification Center Dropdown
function initNotificationDropdown() {
    const bellBtn = document.getElementById('notification-bell-btn');
    const dropdown = document.getElementById('notification-dropdown');

    if (!bellBtn || !dropdown) return;

    bellBtn.addEventListener('click', (e) => {
        e.stopPropagation();
        const isOpen = dropdown.classList.contains('active');
        dropdown.classList.toggle('active', !isOpen);
    });

    document.addEventListener('click', (e) => {
        if (!dropdown.contains(e.target) && e.target !== bellBtn) {
            dropdown.classList.remove('active');
        }
    });
}

// 3. Instant Live Search on Announcements
function initLiveSearch() {
    const searchInput = document.getElementById('live-search-input');
    const announcementCards = document.querySelectorAll('.announcement-card');

    if (!searchInput || !announcementCards.length) return;

    searchInput.addEventListener('input', (e) => {
        const query = e.target.value.toLowerCase().trim();
        let matchCount = 0;

        announcementCards.forEach(card => {
            const title = card.querySelector('.announcement-title')?.textContent.toLowerCase() || '';
            const excerpt = card.querySelector('.announcement-excerpt')?.textContent.toLowerCase() || '';
            const badge = card.querySelector('.badge')?.textContent.toLowerCase() || '';

            if (title.includes(query) || excerpt.includes(query) || badge.includes(query)) {
                card.style.display = 'flex';
                matchCount++;
            } else {
                card.style.display = 'none';
            }
        });

        const noResultsEl = document.getElementById('no-search-results');
        if (noResultsEl) {
            noResultsEl.style.display = matchCount === 0 ? 'block' : 'none';
        }
    });
}

// 4. Acknowledgement Modal Handling
function initAcknowledgementModal() {
    const openBtn = document.getElementById('open-ack-modal-btn');
    const modal = document.getElementById('ack-modal');
    const closeBtn = document.getElementById('close-ack-modal-btn');
    const cancelBtn = document.getElementById('cancel-ack-btn');

    if (!modal) return;

    if (openBtn) {
        openBtn.addEventListener('click', () => {
            modal.classList.add('active');
        });
    }

    const closeModal = () => modal.classList.remove('active');

    if (closeBtn) closeBtn.addEventListener('click', closeModal);
    if (cancelBtn) cancelBtn.addEventListener('click', closeModal);

    modal.addEventListener('click', (e) => {
        if (e.target === modal) closeModal();
    });
}

// 5. Copy & Quick Credentials Fillers
function initCopyButtons() {
    window.fillCredentials = function(email, password) {
        const emailInput = document.getElementById('email');
        const passInput = document.getElementById('password');
        if (emailInput && passInput) {
            emailInput.value = email;
            passInput.value = password;
            // Highlight effect
            emailInput.style.borderColor = 'var(--primary-light)';
            passInput.style.borderColor = 'var(--primary-light)';
            setTimeout(() => {
                emailInput.style.borderColor = '';
                passInput.style.borderColor = '';
            }, 800);
        }
    };
}

// 6. CSV Export Helper for Admin Reach Reports
function exportTableToCSV(tableId, filename = 'reach-report.csv') {
    const table = document.getElementById(tableId);
    if (!table) return;

    let csv = [];
    const rows = table.querySelectorAll('tr');

    for (let i = 0; i < rows.length; i++) {
        const row = [];
        const cols = rows[i].querySelectorAll('td, th');
        for (let j = 0; j < cols.length; j++) {
            // Clean up text and escape quotes
            let data = cols[j].innerText.replace(/(\r\n|\n|\r)/gm, ' ').trim();
            data = data.replace(/"/g, '""');
            row.push(`"${data}"`);
        }
        csv.push(row.join(','));
    }

    const csvFile = new Blob([csv.join('\n')], { type: 'text/csv' });
    const downloadLink = document.createElement('a');
    downloadLink.download = filename;
    downloadLink.href = window.URL.createObjectURL(csvFile);
    downloadLink.style.display = 'none';
    document.body.appendChild(downloadLink);
    downloadLink.click();
    document.body.removeChild(downloadLink);
}
